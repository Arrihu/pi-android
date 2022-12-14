package app.perdana.indonesia.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ebysofyan on 12/23/19.
 */

data class Menu(
    val title: String,
    val icon: Int,
    val badge: String = "",
    val module: String = ""
)

data class Regional(
    val id: Int,
    val name: String
) {
    override fun toString(): String = name
}

data class Province(
    val id: Int,
    val name: String
) {
    override fun toString(): String = name
}

data class Branch(
    val id: Int,
    val name: String
) {
    override fun toString(): String = name
}

@Parcelize
data class Satuan(
    val id: Int,
    val name: String
) : Parcelable {
    override fun toString(): String = name
}

data class DashboardContainer(
    val data: List<DashboardData>,
    val title: String
)

data class DashboardData(
    val title: String,
    val type: String,
    val value: Int
)