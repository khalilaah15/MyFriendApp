package com.example.myfriendapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyFriendsFragment : Fragment() {
    private var fabAddFriend: FloatingActionButton? = null
    private var listTeman : List<MyFriend>? = null
//    lateinit var listTeman : MutableList<MyFriend>
    private var listMyFriend : RecyclerView? = null
    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null

    companion object {
        fun newInstance() : MyFriendsFragment {
            return MyFriendsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_friends_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocalDB()
        initView()
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDataBase(requireActivity())
        myFriendDao = db?.myFriendDao()
    }

    private fun initView() {
        fabAddFriend = activity?.findViewById(R.id.fabAddFriend)
        listMyFriend = activity?.findViewById(R.id.listMyFriends)
        fabAddFriend?.setOnClickListener {
            (activity as MainActivity).tampilMyFriendsAddFragment()
        }
//        simulasiDataTeman()
//        tampilTeman()
        ambilDataTeman()
    }

    private fun ambilDataTeman() {
        listTeman = ArrayList()
        myFriendDao?.ambilSemuaTeman()?.observe(requireActivity()) {r -> listTeman = r
            when{
                listTeman?.size == 0 -> tampilToast("Belum ada data teman")
                else -> {
                    tampilTeman()
                }
            }
        }
    }

    private fun tampilToast(massage: String) {
        Toast.makeText(requireActivity(), massage, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        fabAddFriend = null
        listMyFriend = null
    }

    private fun simulasiDataTeman() {
        listTeman = ArrayList()
//        listTeman.add(MyFriend("Fatimah Azzahra M.", "Perempuan", "rara@gmail.com", "081236453637", "Probolinggo"))
//        listTeman.add(MyFriend("Meiva Yusnita A. W. K", "Perempuan", "meiva@gmail.com", "081215727699", "Klaten"))
    }

    private fun tampilTeman() {
        listMyFriend?.layoutManager = LinearLayoutManager(activity)
        listMyFriend?.adapter = MyFriendAdapter(requireActivity(), listTeman!! as ArrayList<MyFriend>)
    }
}