package co.kr.woowahan_repo.util

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.showToast(message: String){
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.showSnackBar(view: View, message: String){
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}