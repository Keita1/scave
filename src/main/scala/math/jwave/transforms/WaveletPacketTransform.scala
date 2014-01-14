package math.jwave.transforms

import math.jwave.transforms.wavelets.Wavelet
/**
 * Base class for the forward and reverse Wavelet Packet Transform (WPT) also
 * called Wavelet Packet Decomposition (WPD) using a specified Wavelet by
 * inheriting class.
 */

class WaveletPacketTransform(wavelet: Wavelet, steps: Int = -1) extends WaveletTransform(wavelet, steps) {
  /**
   * Implementation of the 1-D forward wavelet packet transform for arrays of
   * dim N by filtering with the longest wavelet first and then always with both
   * sub bands -- low and high (approximation and details) -- by the next
   * smaller wavelet.
   *
   * @see math.jwave.transforms.BasicTransform#forward(double[])
   */
  protected def forwardTransform(arrHilb: Array[Double], windowSize: Int) = {
    val g = arrHilb.length / windowSize
    for (p <- 0 until g) {
      val iBuf = arrHilb.view.slice(p * windowSize, (p + 1) * windowSize)
      val oBuf = wavelet.forward(iBuf)
      Array.copy(oBuf, 0, arrHilb, p * windowSize, windowSize)
    }
    arrHilb
  }
  /**
   * Implementation of the 1-D reverse wavelet packet transform for arrays of
   * dim N by filtering with the smallest wavelet for all sub bands -- low and
   * high bands (approximation and details) -- and the by the next greater
   * wavelet combining two smaller and all other sub bands.
   *
   * @see math.jwave.transforms.BasicTransform#reverse(double[])
   */
  protected def reverseTransform(arrTime: Array[Double], h: Int) = {
    val g = arrTime.length / h
    for (p <- 0 until g) {
      val iBuf = arrTime.view.slice(p * h, (p + 1) * h)
      val oBuf = wavelet.reverse(iBuf)
      Array.copy(oBuf, 0, arrTime, p * h, h)
    }
    arrTime
  }
}
