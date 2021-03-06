package com.woocommerce.android.ui.products.reviews

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.woocommerce.android.di.ViewModelAssistedFactory
import com.woocommerce.android.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ProductReviewsModule {
    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideDefaultArgs(fragment: ProductReviewsFragment): Bundle? {
            return fragment.arguments
        }
    }

    @Binds
    @IntoMap
    @ViewModelKey(ProductReviewsViewModel::class)
    abstract fun bindFactory(factory: ProductReviewsViewModel.Factory): ViewModelAssistedFactory<out ViewModel>

    @Binds
    abstract fun bindSavedStateRegistryOwner(fragment: ProductReviewsFragment): SavedStateRegistryOwner
}
