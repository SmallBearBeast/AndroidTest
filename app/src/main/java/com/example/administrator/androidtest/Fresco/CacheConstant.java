/*
 * This file provided by Facebook is for non-commercial testing and evaluation
 * purposes only.  Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.administrator.androidtest.Fresco;

import com.facebook.common.util.ByteConstants;

public class CacheConstant {
  private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();

  public static final int MAX_DISK_CACHE_SIZE = 8 * ByteConstants.MB;
  public static final int MAX_DISK_CACHE_SIZE_FOR_SMALL_IMAGE = 1 * ByteConstants.MB;
  public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 8;
  public static final int MAX_MEMORY_CACHE_EVICTION_SIZE = MAX_HEAP_SIZE / 16;
}
