package com.example.rpc20

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import kotlinx.android.parcel.Parcelize

class OnBoardPagesActivity : AppCompatActivity() {
    private lateinit var viewPagerTwo: ViewPager2
    private lateinit var btnArrowNext: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_board_pages)
        val pageFragments = listOf(PageOne(), PageTwo(), PageThree())

        viewPagerTwo = getView(R.id.vpOne)
        btnArrowNext = getView(R.id.ivArrowNext)
        val dotsIndicator = getView<SpringDotsIndicator>(R.id.indicator)
        var playerName = ""
        val pageThree = PageThree()
        pageThree.listener.observe(this,{
            playerName = it
        })

        val adapter = MainAdapter(this, pageFragments)
        viewPagerTwo.adapter = adapter
        dotsIndicator.setViewPager2(viewPagerTwo)

        btnArrowNext.setOnClickListener{
            it.setBackgroundResource(R.color.indicator_selected)

            //TODO next arrow before last fragment
            val player = Player(playerName)
            val intent = Intent(this, Menu()::class.java)
            intent.putExtra("Player", player)
            startActivity(intent)
        }

    }

    class MainAdapter(fa: FragmentActivity, private val pageFragmentsParam: List<Fragment>) : FragmentStateAdapter(fa) {


        override fun getItemCount(): Int = pageFragmentsParam.size
        override fun createFragment(position: Int): Fragment = pageFragmentsParam[position]
    }


}
fun <T : View> AppCompatActivity.getView(id: Int) = this.findViewById<T>(id)

@Parcelize
data class Player(var name: String) : Parcelable