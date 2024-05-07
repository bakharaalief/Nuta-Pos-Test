package com.bakhdev.nutaposttest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bakhdev.nutaposttest.databinding.ItemMoneyInBinding
import com.bakhdev.nutaposttest.databinding.ItemMoneyInDateBinding
import com.bakhdev.nutaposttest.domain.model.Money
import com.bakhdev.nutaposttest.domain.model.MoneyReport
import com.bakhdev.nutaposttest.util.toFormattedDate
import com.bakhdev.nutaposttest.util.toFormattedDateTime
import com.bakhdev.nutaposttest.util.toRupiah

@Suppress("UNCHECKED_CAST")
class ListMoneyInAdapter(
    private val listItem: List<AdapterItem>,
    private val onEditItem: () -> Unit,
    private val onDeleteItem: (money: Money) -> Unit,
    private val onPhotoItem: (path: String) -> Unit,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == AdapterItem.TYPE_HEADER) {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemMoneyInDateBinding.inflate(inflater, parent, false)
            ItemMoneyInDateViewHolder(binding)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemMoneyInBinding.inflate(inflater, parent, false)
            ItemMoneyInViewHolder(binding, onEditItem, onDeleteItem, onPhotoItem)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == AdapterItem.TYPE_HEADER) {
            val adapterHeader: AdapterHeader<MoneyReport> =
                listItem[position] as AdapterHeader<MoneyReport>
            (holder as ItemMoneyInDateViewHolder).data(adapterHeader.data)
        } else {
            val adapterItem: AdapterValue<Money> =
                listItem[position] as AdapterValue<Money>
            (holder as ItemMoneyInViewHolder).data(adapterItem.data)
        }
    }

    class ItemMoneyInDateViewHolder(private val itemMoneyInDateBinding: ItemMoneyInDateBinding) :
        RecyclerView.ViewHolder(itemMoneyInDateBinding.root) {
        fun data(moneyReport: MoneyReport) {
            itemMoneyInDateBinding.apply {
                tvDate.text = moneyReport.date
                tvSum.text = moneyReport.sum.toRupiah()
            }
        }
    }

    class ItemMoneyInViewHolder(
        private val itemMoneyInBinding: ItemMoneyInBinding,
        private val onEditItem: () -> Unit,
        private val onDeleteItem: (money: Money) -> Unit,
        private val onPhotoItem: (path: String) -> Unit
    ) :
        RecyclerView.ViewHolder(itemMoneyInBinding.root) {
        fun data(money: Money) {
            itemMoneyInBinding.apply {
                tvTo?.text = money.to
                tvFrom.text = money.from
                tvNote.text = money.notes
                tvDate?.text = money.date.toFormattedDate()
                tvDateTime?.text = money.date.toFormattedDateTime()
                tvAmount.text = money.amount.toRupiah()
                tvEdit?.setOnClickListener {
                    onEditItem()
                }
                tvDelete?.setOnClickListener {
                    onDeleteItem(money)
                }
                tvPhoto?.visibility = if (money.path.isEmpty()) View.GONE else View.VISIBLE
                tvPhoto?.setOnClickListener {
                    onPhotoItem(money.path)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return listItem[position].type
    }

    override fun getItemCount(): Int = listItem.size
}