package com.makaraya.movapp.util

import androidx.annotation.DrawableRes
import com.makaraya.movapp.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.first,
        title = "Lebih dari 50.000 \n film dan serial",
        description = "Setiap hari kami mengisi katalog dengan produk baru dari studio terkemuka dunia."
    )

    object Second : OnBoardingPage(
        image = R.drawable.second,
        title = "Rekomendasi pribadi",
        description = "Sekarang memilih film adalah suatu kesenangan Sesuaikan rekomendasi Anda"
    )

    object Third : OnBoardingPage(
        image = R.drawable.third,
        title = "Mencari film terbaik di perangkat mu",
        description = "Sekarang kamu bisa mencari film sesuai dengan apa yang kamu inginkan"
    )
}