package com.example.computer.twoactivities

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var mMessageEditText: EditText? = null
    private var mReplyHeadTextView: TextView? = null
    private var mReplyTextView: TextView? = null
    private var constraintLayout: ConstraintLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        constraintLayout = findViewById(R.id.constraint_layout)
        mMessageEditText = findViewById(R.id.editText_main)
        mReplyHeadTextView = findViewById(R.id.text_header_reply)
        mReplyTextView = findViewById(R.id.text_message_reply)
    }

    fun animateToKeyFrameTwo() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val transition = ChangeBounds()
            transition.interpolator = AnticipateOvershootInterpolator(2.0f)

            val constraintSet = ConstraintSet()
            constraintSet.clone(this, R.layout.keyframe_two)

            TransitionManager.beginDelayedTransition(constraintLayout, transition)
            constraintSet.applyTo(constraintLayout)
        }
    }

    fun constraintAnimation(view: View) {
        animateToKeyFrameTwo()
    }

    fun launchSecondActivity(view: View) {
        Log.d(LOG_TAG, "Button clicked")
        val intent = Intent(this, SecondActivity::class.java)
        val message = mMessageEditText!!.text.toString()
        intent.putExtra(EXTRA_MESSAGE, message)
        startActivityForResult(intent, TEXT_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val reply = data!!.getStringExtra(SecondActivity.EXTRA_REPLY)
                mReplyHeadTextView!!.visibility = View.VISIBLE
                mReplyTextView!!.text = reply
                mReplyTextView!!.visibility = View.VISIBLE
            }
        }
    }

    companion object {

        private val LOG_TAG = MainActivity::class.java.simpleName
        const val EXTRA_MESSAGE = "com.example.computer.twoactivities.extra.MESSAGE"
        const val TEXT_REQUEST = 1
    }
}
