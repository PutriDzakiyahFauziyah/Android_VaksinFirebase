package com.example.vaksin_firebase.pasien

import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.vaksin_firebase.R
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class PasienAdapter (private val pasienList:ArrayList<Pasien>) :
    RecyclerView.Adapter<PasienAdapter.PasienViewHolder>() {

    private lateinit var activity: AppCompatActivity

    class PasienViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val nik: TextView = itemView.findViewById(R.id.TVLNik)
       val nama: TextView = itemView.findViewById(R.id.TVLNama)
       val jenis_kelamin: TextView = itemView.findViewById(R.id.TVLJenisKelamin)
       val img_pasien: ImageView = itemView.findViewById(R.id.IMLGambarPasien)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasienViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.pasien_list_layout, parent,false)
        return PasienViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PasienViewHolder, position: Int) {
        val pasien: Pasien = pasienList[position]
        holder.nik.text = pasien.nik
        holder.nama.text = pasien.nama
        holder.jenis_kelamin.text = pasien.jenis_kelamin

        holder.itemView.setOnClickListener {
            activity = it.context as AppCompatActivity
            activity.startActivity(Intent(activity, EditPasienActivity::class.java).apply {
                putExtra("nik", pasien.nama.toString())
                putExtra("nama", pasien.nama.toString())
                putExtra("tgl_lahir", pasien.tgl_lahir.toString())
                putExtra("jenis_kelamin", pasien.jenis_kelamin.toString())
                putExtra("penyakit_bawaan", pasien.penyakit_bawaan.toString())
            })
        }

        val storageRef = FirebaseStorage.getInstance().reference.child("img_pasien/${pasien.nik}_${pasien.nama}.jpg")
        val localFile = File.createTempFile("tempImage", "jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            holder.img_pasien.setImageBitmap(bitmap)
        }.addOnFailureListener {
            Log.e("foto ?", "gagal")
        }
    }

    override fun getItemCount(): Int {
        return pasienList.size
    }
}