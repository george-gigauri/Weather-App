package ge.george.openweather.data.network

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): Resource<T> =
            Resource(Status.SUCCESS, data, null)

        fun <T> error(message: String?): Resource<T> =
            Resource(Status.ERROR, null, message ?: "Unknown Error Occurred")

        fun <T> loading(): Resource<T> =
            Resource(Status.LOADING, null, null)
    }

}