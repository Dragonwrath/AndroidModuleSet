package com.joker.utils.kt.view

import android.animation.Animator

open class SimpleAnimatorListener(
    private val onAnimationRepeat : ((animation : Animator?)->Unit)? = null,
    private val onAnimationEnd : ((animation : Animator?)->Unit)? = null,
    private val onAnimationCancel : ((animation : Animator?)->Unit)? = null,
    private val onAnimationStart : ((animation : Animator?)->Unit)? = null
) : Animator.AnimatorListener {
  /**
   *
   * Notifies the repetition of the animation.
   *
   * @param animation The animation which was repeated.
   */
  override fun onAnimationRepeat(animation : Animator?) {
    onAnimationRepeat?.invoke(animation)
  }

  /**
   *
   * Notifies the end of the animation. This callback is not invoked
   * for animations with repeat count set to INFINITE.
   *
   * @param animation The animation which reached its end.
   */
  override fun onAnimationEnd(animation : Animator?) {
    onAnimationEnd?.invoke(animation)
  }

  /**
   *
   * Notifies the cancellation of the animation. This callback is not invoked
   * for animations with repeat count set to INFINITE.
   *
   * @param animation The animation which was canceled.
   */
  override fun onAnimationCancel(animation : Animator?) {
    onAnimationCancel?.invoke(animation)
  }

  /**
   *
   * Notifies the start of the animation.
   *
   * @param animation The started animation.
   */
  override fun onAnimationStart(animation : Animator?) {
    onAnimationStart?.invoke(animation)
  }

}