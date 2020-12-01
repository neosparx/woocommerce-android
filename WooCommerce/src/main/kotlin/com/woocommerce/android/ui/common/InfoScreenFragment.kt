package com.woocommerce.android.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.navigation.fragment.navArgs
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.woocommerce.android.R
import com.woocommerce.android.analytics.AnalyticsTracker
import com.woocommerce.android.extensions.hide
import com.woocommerce.android.ui.common.InfoScreenFragment.InfoScreenLinkAction.LearnMoreAboutShippingLabels
import com.woocommerce.android.util.ChromeCustomTabUtils
import kotlinx.android.synthetic.main.fragment_info_screen.*
import java.io.Serializable

class InfoScreenFragment : Fragment() {
    private val navArgs: InfoScreenFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_info_screen, container, false)
    }

    override fun onResume() {
        super.onResume()

        AnalyticsTracker.trackViewShown(this)

        activity?.let {
            it.invalidateOptionsMenu()
            if (navArgs.screenTitle != 0) it.title = getString(navArgs.screenTitle)
            (it as? AppCompatActivity)
                ?.supportActionBar
                ?.setHomeAsUpIndicator(R.drawable.ic_gridicons_cross_24dp)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        info_heading.showTextOrHide(navArgs.heading)
        info_message.showTextOrHide(navArgs.message)
        info_link.showTextOrHide(navArgs.linkTitle)

        if (navArgs.imageResource != 0) {
            info_image.setImageDrawable(ContextCompat.getDrawable(requireContext(), navArgs.imageResource))
        }

        navArgs.linkAction?.let { action ->
            when (action) {
                is LearnMoreAboutShippingLabels -> info_link.setOnClickListener {
                    ChromeCustomTabUtils.launchUrl(requireContext(), action.LINK)
                }
            }
        }
    }

    private fun TextView.showTextOrHide(@StringRes stringRes: Int) =
        if (stringRes != 0) this.text = getString(stringRes) else hide()

    sealed class InfoScreenLinkAction : Serializable {
        object LearnMoreAboutShippingLabels : InfoScreenLinkAction() {
            const val LINK: String = "https://woocommerce.com/products/shipping/"
        }
    }
}
