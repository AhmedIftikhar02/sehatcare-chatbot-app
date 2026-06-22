-if class com.app.sehatcare.home.data.remote.ProductListResponse
-keepnames class com.app.sehatcare.home.data.remote.ProductListResponse
-if class com.app.sehatcare.home.data.remote.ProductListResponse
-keep class com.app.sehatcare.home.data.remote.ProductListResponseJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
