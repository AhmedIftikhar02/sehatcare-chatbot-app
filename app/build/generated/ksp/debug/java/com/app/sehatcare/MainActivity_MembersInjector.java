package com.app.sehatcare;

import com.app.sehatcare.common.bus.LoginEventBus;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<LoginEventBus> loginEventBusProvider;

  public MainActivity_MembersInjector(Provider<LoginEventBus> loginEventBusProvider) {
    this.loginEventBusProvider = loginEventBusProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<LoginEventBus> loginEventBusProvider) {
    return new MainActivity_MembersInjector(loginEventBusProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectLoginEventBus(instance, loginEventBusProvider.get());
  }

  @InjectedFieldSignature("com.app.sehatcare.MainActivity.loginEventBus")
  public static void injectLoginEventBus(MainActivity instance, LoginEventBus loginEventBus) {
    instance.loginEventBus = loginEventBus;
  }
}
