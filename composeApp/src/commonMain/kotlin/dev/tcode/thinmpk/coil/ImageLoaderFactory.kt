package dev.tcode.thinmpk.coil

import coil3.ImageLoader
import coil3.PlatformContext

expect fun newImageLoader(context: PlatformContext): ImageLoader
