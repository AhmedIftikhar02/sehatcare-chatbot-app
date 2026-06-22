package com.app.sehatcare.chat.data.repository;

import com.app.sehatcare.chat.data.remote.ChatApiService;
import com.app.sehatcare.core.network.NetworkMonitor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class ChatRepositoryImpl_Factory implements Factory<ChatRepositoryImpl> {
  private final Provider<ChatApiService> apiProvider;

  private final Provider<NetworkMonitor> networkMonitorProvider;

  public ChatRepositoryImpl_Factory(Provider<ChatApiService> apiProvider,
      Provider<NetworkMonitor> networkMonitorProvider) {
    this.apiProvider = apiProvider;
    this.networkMonitorProvider = networkMonitorProvider;
  }

  @Override
  public ChatRepositoryImpl get() {
    return newInstance(apiProvider.get(), networkMonitorProvider.get());
  }

  public static ChatRepositoryImpl_Factory create(Provider<ChatApiService> apiProvider,
      Provider<NetworkMonitor> networkMonitorProvider) {
    return new ChatRepositoryImpl_Factory(apiProvider, networkMonitorProvider);
  }

  public static ChatRepositoryImpl newInstance(ChatApiService api, NetworkMonitor networkMonitor) {
    return new ChatRepositoryImpl(api, networkMonitor);
  }
}
