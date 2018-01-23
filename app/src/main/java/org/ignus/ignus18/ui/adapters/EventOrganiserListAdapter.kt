package org.ignus.ignus18.ui.adapters

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.ignus.ignus18.App
import org.ignus.ignus18.R
import org.ignus.ignus18.data.Organiser
import org.ignus.ignus18.ui.utils.ctx

class EventOrganiserListAdapter(private val list: List<Organiser>) : RecyclerView.Adapter<EventOrganiserListAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.ctx).inflate(R.layout.event_organiser_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.avatar)
                .load(list[position].avatar_url)
                .apply(RequestOptions().circleCrop())
                .into(holder.avatar)

        holder.name?.text = list[position].name
        holder.number?.text = list[position].phone

        holder.call?.setOnClickListener({
            holder.avatar.context?.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + list[position].phone)))
        })

        holder.whatsapp?.setOnClickListener({
            val whatsNum: String = if (list[position].phone.length != 10) list[position].phone else "91" + list[position].phone
            holder.avatar.context?.startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://api.whatsapp.com/send?phone=" + whatsNum)))
        })

        holder.email?.setOnClickListener({
            val i = Intent(Intent.ACTION_SENDTO)
            i.type = "message/rfc822"
            i.data = Uri.parse("mailto:${list[position].email}")
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf(list[position].email))
            i.putExtra(Intent.EXTRA_SUBJECT, "Ignus 2018")
            i.putExtra(Intent.EXTRA_TEXT, "Hi ${list[position].name}")
            App.instance.startActivity(i)
        })
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: ImageView = view.findViewById(R.id.edo_avatar)
        val name: TextView? = view.findViewById(R.id.edo_name)
        val number: TextView? = view.findViewById(R.id.edo_number)
        val call: ImageView? = view.findViewById(R.id.edo_call)
        val whatsapp: ImageView? = view.findViewById(R.id.edo_whatsApp)
        val email: ImageView? = view.findViewById(R.id.edo_email)
    }
}