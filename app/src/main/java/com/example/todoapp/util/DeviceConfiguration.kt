package com.example.todoapp.util

import androidx.window.core.layout.WindowSizeClass


enum class DeviceConfiguration {
    MOBILE_PORTRAIT,
    MOBILE_LANDSCAPE,
    TABLET_PORTRAIT,
    TABLET_LANDSCAPE,
    DESKTOP;

    companion object {
        fun fromWindowClassSize(windowSizeClass: WindowSizeClass): DeviceConfiguration {
            //Width Checks
            val isMediumWidth = windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) // 600dp+
            val isExpandedWidth = windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) // 840dp+
            val isDesktopWidth = windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_LARGE_LOWER_BOUND) // 1200dp+

            //Height Checks
            val isMediumHeight = windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND) // 480dp+

            //TODO Will remove deprecated code later
           /* val widthClass = windowSizeClass.windowWidthSizeClass
            val heightClass = windowSizeClass.windowHeightSizeClass*/
            return when {
                // 1. DESKTOP: Width 1200dp+ (Large/Extra-Large)
                isDesktopWidth -> DESKTOP

                // 2. TABLET LANDSCAPE: Width 840dp+ (Expanded) and Height is at least Medium
                isExpandedWidth -> TABLET_LANDSCAPE

                // 3. TABLET PORTRAIT: Width 600dp+ (Medium)
                isMediumWidth -> TABLET_PORTRAIT

                // 4. MOBILE LANDSCAPE: Width is < 600dp but height is Compact (< 480dp)
                // Typically, phones in landscape have a Compact height class.
                !isMediumHeight -> MOBILE_LANDSCAPE

                // 5. MOBILE PORTRAIT: Default state (Width < 600dp, Height > 480dp)
                else -> MOBILE_PORTRAIT

                //TODO Will remove deprecated code later
               /* WindowWidthSizeClass.COMPACT if heightClass == WindowHeightSizeClass.MEDIUM -> MOBILE_PORTRAIT
                WindowWidthSizeClass.COMPACT if heightClass == WindowHeightSizeClass.EXPANDED -> MOBILE_PORTRAIT
                WindowWidthSizeClass.EXPANDED if heightClass == WindowHeightSizeClass.COMPACT -> MOBILE_LANDSCAPE
                WindowWidthSizeClass.MEDIUM if heightClass == WindowHeightSizeClass.EXPANDED -> TABLET_PORTRAIT
                WindowWidthSizeClass.EXPANDED if heightClass == WindowHeightSizeClass.MEDIUM -> TABLET_LANDSCAPE
                else -> DESKTOP*/
            }
        }
    }
}