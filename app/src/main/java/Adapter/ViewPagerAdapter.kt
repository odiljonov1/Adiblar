package Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.odiljonov.adiblar.AdiblarFragment
import com.odiljonov.adiblar.SaqlanganFragment
import com.odiljonov.adiblar.SettingsFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        return when (position) {

            0 -> {
                AdiblarFragment()
            }
            1 -> {
                SaqlanganFragment()
            }
            else -> {
                SettingsFragment()
            }

        }
    }
}