package Adapter

import Cache.MySharedPreference
import Model.Adib
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.odiljonov.adiblar.R
import com.odiljonov.adiblar.databinding.ItemRvBinding
import com.squareup.picasso.Picasso
import java.io.Serializable

class RvAdapter(
    val context: Context?,
    var list: ArrayList<Adib>,
    var rvClick: RvClick,
    var saqNoSaq: Int = 0
) :
    RecyclerView.Adapter<RvAdapter.VH>(), Serializable {

    inner class VH(var itemRv: ItemRvBinding) : RecyclerView.ViewHolder(itemRv.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun onBind(adib: Adib, position: Int) {
            itemRv.tvNameAdibRv.text = adib.nameAndLastname
            itemRv.tvDateAdibRv.text = "(${adib.tugilganYili}-${adib.olganYili})"
            Picasso.get().load(adib.imageUri).into(itemRv.imageRvAdib)

            MySharedPreference.init(context)
//            println(_root_ide_package_.Cache.MySharedPreference.obektString)
            var index = -1
            var mList = MySharedPreference.objectString
            for (n in mList.indices) {
                println("mList[n] = ${mList[n].imageUri} == ${adib.imageUri}")
                if (mList[n].imageUri == adib.imageUri) {
                    index = n
                    break
                }
            }
            println("index: $index")
            if (index != -1) {
                itemRv.imageSelection.setImageResource(R.drawable.saqlangan_2)
            } else {
                itemRv.imageSelection.setImageResource(R.drawable.saqlangan_1)
            }

            itemRv.root.setOnClickListener { rvClick.onCLick(adib) }
            itemRv.imageSelection.setOnClickListener {

                if (index != -1) {
//                    itemRv.imageSelection.setImageResource(R.drawable.ic_saqlangan_1)
                    val l = MySharedPreference.objectString
                    println("" + l.removeAt(index) + "removed ")
                    MySharedPreference.objectString = l
                } else {
//                    itemRv.imageSelection.setImageResource(R.drawable.ic_saqlangan_2)
                    val l = MySharedPreference.objectString
                    l.add(adib)
                    MySharedPreference.objectString = l
                }
                println("saqNoSaq : $saqNoSaq")
                if (saqNoSaq == 1) {
                    list.remove(adib)
                    notifyItemRemoved(position)
                    notifyItemRangeRemoved(position, mList.size)
                } else {
                    notifyItemChanged(position)
                }
            }
        }

    }

    interface RvClick {
        fun onCLick(adib: Adib)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredList: ArrayList<Adib>) {
        list = filteredList
        notifyDataSetChanged()
    }
}