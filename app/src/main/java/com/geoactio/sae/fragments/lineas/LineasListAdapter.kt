package com.geoactio.sae.fragments.lineas

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.geoactio.domain.Linea
import com.geoactio.sae.databinding.LineasListRowBinding

class LineasListAdapter(val lineas: ArrayList<Linea>, val context: Context): BaseAdapter() {
    override fun getCount(): Int {
        return lineas.size
    }

    override fun getItem(p0: Int): Any {
        return lineas[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val viewHolder: ViewHolder
        if (p1 == null) {
            val convertView = LineasListRowBinding.inflate(LayoutInflater.from(context))
            viewHolder = ViewHolder(convertView)
            viewHolder.view = convertView.root
            convertView.root.tag = viewHolder
        } else {
            viewHolder = p1.tag as ViewHolder
        }

        val linea = lineas[p0]
        viewHolder.binding.idLineaLabel.text = linea.id
        viewHolder.binding.nombreLineaLabel.text = linea.nombre
        viewHolder.binding.lineaColorView.setBackgroundColor(Color.parseColor(linea.color))

        return viewHolder.view
    }
}

private class ViewHolder(binding: LineasListRowBinding) {
    var view = binding.root
    val binding = binding
}
