package com.weizhan.superlook.widget.banners;

import android.support.v4.view.ViewPager.PageTransformer;

import com.weizhan.superlook.widget.banners.transformer.AccordionTransformer;
import com.weizhan.superlook.widget.banners.transformer.BackgroundToForegroundTransformer;
import com.weizhan.superlook.widget.banners.transformer.CubeInTransformer;
import com.weizhan.superlook.widget.banners.transformer.CubeOutTransformer;
import com.weizhan.superlook.widget.banners.transformer.DefaultTransformer;
import com.weizhan.superlook.widget.banners.transformer.DepthPageTransformer;
import com.weizhan.superlook.widget.banners.transformer.FlipHorizontalTransformer;
import com.weizhan.superlook.widget.banners.transformer.FlipVerticalTransformer;
import com.weizhan.superlook.widget.banners.transformer.ForegroundToBackgroundTransformer;
import com.weizhan.superlook.widget.banners.transformer.RotateDownTransformer;
import com.weizhan.superlook.widget.banners.transformer.RotateUpTransformer;
import com.weizhan.superlook.widget.banners.transformer.ScaleInOutTransformer;
import com.weizhan.superlook.widget.banners.transformer.StackTransformer;
import com.weizhan.superlook.widget.banners.transformer.TabletTransformer;
import com.weizhan.superlook.widget.banners.transformer.ZoomInTransformer;
import com.weizhan.superlook.widget.banners.transformer.ZoomOutSlideTransformer;
import com.weizhan.superlook.widget.banners.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
