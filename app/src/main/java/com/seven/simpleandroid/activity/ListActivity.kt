package com.seven.simpleandroid.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.seven.simpleandroid.R
import com.seven.simpleandroid.adapter.ClassAdapter
import com.seven.simpleandroid.interfaces.IItemClickListener
import com.seven.simpleandroid.model.ClassModel
import com.seven.simpleandroid.utils.Permission
import com.seven.simpleandroid.widget.ItemDecoration
import kotlinx.android.synthetic.main.list_activity.*

class ListActivity : AppCompatActivity(), IItemClickListener<ClassModel> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_activity)

        findView()
        bindData()
        bindEvent()
    }

    private fun findView() {

    }

    private fun bindData() {
        val data = mutableListOf<ClassModel>()
        data.add(ClassModel("SwipeRefresh"))
        data.add(ClassModel("Drawer1", "Drawer layout is super layout"))
        data.add(ClassModel("Drawer2", "Drawer layout is child layout"))
        data.add(ClassModel("BottomNavigation"))
        data.add(ClassModel("ScrollingActivity"))
        data.add(ClassModel("RecyclerView", "Vertical, Horizontal, Grid_Vertical, Grid_Horizontal"))
        data.add(ClassModel("ToolbarActivity", "CollapsingToolbarLayout, TabLayout"))
        data.add(ClassModel("NestedActivity"))
        data.add(ClassModel("FragmentActivity"))
        data.add(ClassModel("MultipleTabActivity"))
        data.add(ClassModel("BannerActivity"))
        data.add(ClassModel("MoveActivity"))
        data.add(ClassModel("QRCodeActivity"))
        data.add(ClassModel("WifiActivity"))
        data.add(ClassModel("PermissionActivity"))
        data.add(ClassModel("SharedElementActivity"))

        val adapter = ClassAdapter(this, data)
        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.addItemDecoration(ItemDecoration(this))
        rvMain.adapter = adapter
    }

    private fun bindEvent() {
        val adapter = rvMain.adapter as ClassAdapter
        adapter.itemClick = this
    }

    override fun itemClicked(view: View, position: Int, model: ClassModel) {
        val tempClass = when (model.name) {
            "SwipeRefresh" -> SwipeActivity::class.java
            "Drawer1" -> Drawer1Activity::class.java
            "Drawer2" -> Drawer2Activity::class.java
            "BottomNavigation" -> BottomNavActivity::class.java
            "ScrollingActivity" -> ScrollingActivity::class.java
            "RecyclerView" -> RecyclerViewActivity::class.java
            "ToolbarActivity" -> ToolbarActivity::class.java
            "NestedActivity" -> NestedActivity::class.java
            "FragmentActivity" -> FragmentActivity::class.java
            "MultipleTabActivity" ->MultipleTabActivity::class.java
            "BannerActivity" -> BannerActivity::class.java
            "MoveActivity" -> MoveActivity::class.java
            "QRCodeActivity" -> QRCodeActivity::class.java
            "WifiActivity" -> WifiActivity::class.java
            "PermissionActivity" -> PermissionActivity::class.java
            "SharedElementActivity" -> SharedElementActivity::class.java
            else -> null
        }

        if (null == tempClass) {
            Snackbar.make(rvMain, model.name + " is coding!", Snackbar.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, tempClass)
            startActivity(intent)
        }
    }

    override fun itemLongClicked(view: View, position: Int, model: ClassModel) {
        Snackbar.make(rvMain, model.name + " Long Clicked!", Snackbar.LENGTH_SHORT).show()
    }
}