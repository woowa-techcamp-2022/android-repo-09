package co.kr.woowahan_repo.util

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showToast(message: String){
    Toast.makeText(requireContext().applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showSnackBar(view: View, message: String){
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}