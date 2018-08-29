package com.joker.utils.kt.view

import android.support.design.widget.TabLayout

open class SimpleTableSelectedListener(private val onTabReselected:((tab:TabLayout.Tab?)->Unit)?=null,
                                                private val onTabUnselected:((tab:TabLayout.Tab?)->Unit)?=null,
                                                private val onTabSelected:((tab:TabLayout.Tab?)->Unit)?=null
):TabLayout.OnTabSelectedListener {
 /**
  * Called when a tab that is already selected is chosen again by the user. Some applications
  * may use this action to return to the top level of a category.
  *
  * @param tab The tab that was reselected.
  */
 override fun onTabReselected(tab : TabLayout.Tab?) {
  onTabReselected?.invoke(tab)
 }

 /**
  * Called when a tab exits the selected state.
  *
  * @param tab The tab that was unselected
  */
 override fun onTabUnselected(tab : TabLayout.Tab?) {
  onTabUnselected?.invoke(tab)
 }

 /**
  * Called when a tab enters the selected state.
  *
  * @param tab The tab that was selected
  */
 override fun onTabSelected(tab : TabLayout.Tab?) {
  onTabSelected?.invoke(tab)
 }

}