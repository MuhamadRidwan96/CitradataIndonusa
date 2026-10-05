package com.example.domain.utils

import retrofit2.HttpException
import java.io.IOException

fun Throwable.toUserMessage(): String {
    return when (this) {
        is IOException ->
            "Tidak dapat terhubung ke server. Periksa koneksi internet Anda."

        is HttpException -> when (code()) {
            401 -> "Sesi Anda telah berakhir. Silakan login kembali."
            403 -> "Anda tidak memiliki akses untuk data ini."
            404 -> "Data project tidak ditemukan."
            408 -> "Permintaan terlalu lama. Silakan coba lagi."
            in 500..599 -> "Terjadi gangguan pada server. Silakan coba lagi."
            else -> "Terjadi kesalahan saat memuat data."
        }

        else ->
            "Terjadi kesalahan saat memuat detail project."
    }
}