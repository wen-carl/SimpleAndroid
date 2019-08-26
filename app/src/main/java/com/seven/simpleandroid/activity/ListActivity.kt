package com.seven.simpleandroid.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.iid.FirebaseInstanceId
import com.seven.simpleandroid.R
import com.seven.simpleandroid.adapter.ClassAdapter
import com.seven.simpleandroid.interfaces.IItemClickListener
import com.seven.simpleandroid.model.ClassModel
import com.seven.simpleandroid.widget.ItemDecoration
import kotlinx.android.synthetic.main.list_activity.*

class ListActivity : AppCompatActivity(), IItemClickListener<ClassModel> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_activity)

        findView()
        bindData()
        bindEvent()

        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("ListActivity", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast
                    //val msg = getString(R.string.msg_token_fmt, token)
                    //Log.d("ListActivity", msg)
                    Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
                })

        val available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        if (available != ConnectionResult.SUCCESS) {
            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this)
        }
    }

    private fun findView() {

    }

    private fun bindData() {
        val data = mutableListOf<ClassModel>()
        data.add(ClassModel(SwipeActivity::class.java.name))
        data.add(ClassModel(Drawer1Activity::class.java.name, "Drawer layout is super layout"))
        data.add(ClassModel(Drawer2Activity::class.java.name, "Drawer layout is child layout"))
        data.add(ClassModel(BottomNavActivity::class.java.name))
        data.add(ClassModel(ScrollingActivity::class.java.name))
        data.add(ClassModel(RecyclerViewActivity::class.java.name, "Vertical, Horizontal, Grid_Vertical, Grid_Horizontal"))
        data.add(ClassModel(ToolbarActivity::class.java.name, "CollapsingToolbarLayout, TabLayout"))
        data.add(ClassModel(NestedActivity::class.java.name))
        data.add(ClassModel(FragmentActivity::class.java.name))
        data.add(ClassModel(MultipleTabActivity::class.java.name))
        data.add(ClassModel(BannerActivity::class.java.name))
        data.add(ClassModel(MoveActivity::class.java.name))
        data.add(ClassModel(QRCodeActivity::class.java.name))
        data.add(ClassModel(WifiActivity::class.java.name))
        data.add(ClassModel(PermissionActivity::class.java.name))
        data.add(ClassModel(SharedElementActivity::class.java.name))
        data.add(ClassModel(NestedViewPagerActivity::class.java.name))
        data.add(ClassModel(LazyLoadingFragmentActivity::class.java.name))
        data.add(ClassModel(TabbedActivity::class.java.name))
        data.add(ClassModel(PrintActivity::class.java.name))
        data.add(ClassModel(TouchEventActivity::class.java.name))
        val adapter = ClassAdapter(this, data)

        rvMain.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rvMain.addItemDecoration(ItemDecoration(this))
        rvMain.adapter = adapter
    }

    private fun bindEvent() {
        val adapter = rvMain.adapter as ClassAdapter
        adapter.itemClick = this
    }

    override fun itemClicked(view: View, position: Int, model: ClassModel) = try {
        val tempClass = Class.forName(model.name) as Class<out AppCompatActivity>?
        val intent = Intent(this, tempClass)
        if (null != intent.resolveActivity(packageManager)) {
            startActivity(intent)
        } else {
            Snackbar.make(rvMain, model.name + " is coding!", Snackbar.LENGTH_SHORT).show()
        }
    } catch (e : ClassNotFoundException) {
        Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    override fun itemLongClicked(view: View, position: Int, model: ClassModel) {
        Snackbar.make(rvMain, model.name + " Long Clicked!", Snackbar.LENGTH_SHORT).show()
    }
}