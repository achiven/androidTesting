import android.os.Parcel
import android.os.Parcelable

data class RepoModel(
    val name: String? = "noname",            // If you see "noname", something went wrong.
    val abbreviation: String? = "",
    val capital: String? = "",
    val largest_city: String? = "",
    val established_date: String? = "2020-12-14T12:00:00-00:00",
    val population: Long,
    val total_area_km2: Long,
    val land_area_km2: Long,
    val water_area_km2: Long,
    val number_of_reps: Long
    ) : Parcelable{

    fun getNameTitle() = "name"
    fun getAbbreviationTitle() = "abbreviation"
    fun getCapitalTitle() = "capital"
    fun getLargest_cityTitle() = "largest"
    fun getEstablished_dateTitle() = "established_date"
    fun getPopulationTitle() = "population"
    fun getTotal_area_km2Title() = "total_area_km2"
    fun getLand_area_km2Title() = "land_area_km2"
    fun getWater_area_km2Title() = "water_area_km2"
    fun getNumber_of_repsTitle() = "number_of_reps"

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(abbreviation)
        parcel.writeString(capital)
        parcel.writeString(largest_city)
        parcel.writeString(established_date)
        parcel.writeLong(population)
        parcel.writeLong(total_area_km2)
        parcel.writeLong(land_area_km2)
        parcel.writeLong(water_area_km2)
        parcel.writeLong(number_of_reps)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RepoModel> {
        override fun createFromParcel(parcel: Parcel): RepoModel {
            return RepoModel(parcel)
        }

        override fun newArray(size: Int): Array<RepoModel?> {
            return arrayOfNulls(size)
        }
    }

}