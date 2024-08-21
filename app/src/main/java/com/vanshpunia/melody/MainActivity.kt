package com.vanshpunia.melody

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.vanshpunia.melody.adapters.CategoryAdaptor
import com.vanshpunia.melody.adapters.SingersAdapter
import com.vanshpunia.melody.adapters.TrendingRvAdaptor
import com.vanshpunia.melody.databinding.ActivityMainBinding
import com.vanshpunia.melody.models.Category
import com.vanshpunia.melody.models.Song
import com.vanshpunia.melody.utils.CATEGORY
import com.vanshpunia.melody.utils.SINGERS
import com.vanshpunia.melody.utils.SONGS
import com.vanshpunia.melody.utils.USER_NODE

class MainActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var exoPlayer: ExoPlayer

    var categoryList = ArrayList<Category>()
    lateinit var categoryAdaptor: CategoryAdaptor
    var singersList = ArrayList<Category>()
    lateinit var singersAdapter: SingersAdapter
    lateinit var sectionAdapter: TrendingRvAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        setContentView(binding.root)

        Firebase.firestore.collection(USER_NODE)
            .document(Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener {
                Glide.with(this@MainActivity).load(it.get("image"))
                    .circleCrop()
                    .into(binding.user)
            }

        binding.user.setOnClickListener {
            startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
        }

        setCategories()
        setSingers()
        setSection("trending", binding.trendingLayout, binding.trending, binding.trendingRv, binding.trendingHeader)
        setSection("podcasts", binding.podcastLayout, binding.podcast, binding.podcastRv, binding.podcastHeader)
        setSection("favourites", binding.favouriteLayout, binding.favourite, binding.favouriteRv, binding.favouriteHeader)
        setMostViewedSection("most_viewed", binding.mostViewedLayout, binding.mostViewed, binding.mostViewedRv, binding.mostViewedHeader)

    }

    override fun onResume() {
        super.onResume()
        showMusicPLayer()
    }

    @OptIn(UnstableApi::class)
    fun showMusicPLayer() {
        MyExoPlayer.currentSong()?.let {
            binding.musicPlaying.visibility = View.VISIBLE
            exoPlayer = MyExoPlayer.getInstance()!!
            binding.playerView.player = exoPlayer
            binding.playerView.showController()
            binding.songName.text = it.title
            binding.singerName.text = it.singer
            Glide.with(this@MainActivity).load(it.coverUrl)
                .apply(RequestOptions().transform(RoundedCorners(32)))
                .into(binding.currentSongImage)
            binding.playerView.setOnClickListener {
                startActivity(Intent(this@MainActivity, PlayerActivity::class.java))
                PlayerActivity.category = ""
            }
        } ?: run {
            binding.musicPlaying.visibility = View.GONE
        }
    }

    // For Categories
    private fun setCategories() {
        Firebase.firestore.collection(CATEGORY).get().addOnSuccessListener {
            categoryList.clear()
            var tempList = ArrayList<Category>()
            for (i in it.documents) {
                var category: Category = i.toObject<Category>()!!
                tempList.add(category)
            }
            categoryList.addAll(tempList)
            categoryAdaptor = CategoryAdaptor(this@MainActivity, categoryList)
            binding.categoryRv.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.categoryRv.adapter = categoryAdaptor
            categoryAdaptor.notifyDataSetChanged()
        }
    }

    // For Singers
    private fun setSingers() {
        Firebase.firestore.collection(SINGERS).get().addOnSuccessListener {
            singersList.clear()
            var tempList = ArrayList<Category>()
            for (i in it.documents) {
                var category: Category = i.toObject<Category>()!!
                tempList.add(category)
            }
            singersList.addAll(tempList)
            singersAdapter = SingersAdapter(this@MainActivity, singersList)
            binding.singerRv.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.singerRv.adapter = singersAdapter
            singersAdapter.notifyDataSetChanged()
        }
    }

    // For Section
    private fun setSection(
        doc: String,
        sectionLayout: LinearLayout,
        sectionName: TextView,
        sectionRV: RecyclerView,
        sectionHeader: ConstraintLayout
    ) {
        Firebase.firestore.collection("section").document(doc).get()
            .addOnSuccessListener {
                sectionLayout.visibility = View.VISIBLE
                val section = it.toObject<Category>()!!
                sectionName.text = section.name
                sectionAdapter =
                    TrendingRvAdaptor(this@MainActivity, section.songs)
                sectionRV.layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                sectionRV.adapter = sectionAdapter
                sectionAdapter.notifyDataSetChanged()

                sectionHeader.setOnClickListener {
                    SongListActivity.category = section
                    startActivity(Intent(this@MainActivity, SongListActivity::class.java))
                }

            }
    }

    // For Section
    private fun setMostViewedSection(
        doc: String,
        sectionLayout: LinearLayout,
        sectionName: TextView,
        sectionRV: RecyclerView,
        sectionHeader: ConstraintLayout
    ) {
        Firebase.firestore.collection("section").document(doc).get()
            .addOnSuccessListener {

                Firebase.firestore.collection(SONGS)
                    .orderBy("views", Query.Direction.DESCENDING)
                    .limit(10)
                    .get().addOnSuccessListener { song->
                        val songs = song.toObjects<Song>()
                        val songList = songs.map{
                            it.id.toString()
                        }.toList()

                        sectionLayout.visibility = View.VISIBLE
                        val section = it.toObject<Category>()!!
                        sectionName.text = section.name
                        section.songs = songList
                        sectionAdapter =
                            TrendingRvAdaptor(this@MainActivity, section.songs)
                        sectionRV.layoutManager =
                            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                        sectionRV.adapter = sectionAdapter
                        sectionAdapter.notifyDataSetChanged()
                        sectionHeader.setOnClickListener {
                            SongListActivity.category = section
                            startActivity(Intent(this@MainActivity, SongListActivity::class.java))
                        }
                    }
            }
    }
}