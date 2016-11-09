//
// Created by Naman on 09/11/16.
//

#include "lodepng.h"
#include <iostream>
#include <string>
#include <sstream>

extern "C" {
const char *getPngInfo(const char *path) {

    std::string pngdetail;
    std::stringstream infostream;
    const std::string filename(path);

    std::vector<unsigned char> buffer;
    std::vector<unsigned char> image;
    unsigned w, h;

    lodepng::load_file(buffer, filename); //load the image file with given filename

    lodepng::State state;

    unsigned error = lodepng::decode(image, w, h, state, buffer);

    if (error) {
        infostream << "Error decoding png. Make sure the file is a valid png and Read external storage permission is granted";
        pngdetail += infostream.str();
        return strdup(pngdetail.c_str());
    }

    infostream << "PNG Info - \n";


    infostream << "Filesize: " << buffer.size() << " (" << buffer.size() / 1024 << "K)" << "\n";
    infostream << "Width: " << w << "\n";
    infostream << "Height: " << h << "\n";
    infostream << "Num pixels: " << w * h << "\n";

    if (w > 0 && h > 0) {
        infostream << "Top left pixel color:"
        << " r: " << (int) image[0]
        << " g: " << (int) image[1]
        << " b: " << (int) image[2]
        << " a: " << (int) image[3] << "\n";
    }

    const LodePNGInfo &info = state.info_png;
    const LodePNGColorMode &color = info.color;

    infostream << "Compression method: " << info.compression_method << "\n";
    infostream << "Filter method: " << info.filter_method << "\n";
    infostream << "Interlace method: " << info.interlace_method << "\n";
    infostream << "Color type: " << color.colortype << "\n";
    infostream << "Bit depth: " << color.bitdepth << "\n";
    infostream << "Bits per pixel: " << lodepng_get_bpp(&color) << "\n";
    infostream << "Channels per pixel: " << lodepng_get_channels(&color) << "\n";
    infostream << "Is greyscale type: " << lodepng_is_greyscale_type(&color) << "\n";
    infostream << "Can have alpha: " << lodepng_can_have_alpha(&color) << "\n";
    infostream << "Palette size: " << color.palettesize << "\n";
    infostream << "Has color key: " << color.key_defined << "\n";
    if (color.key_defined) {
        infostream << "Color key r: " << color.key_r << "\n";
        infostream << "Color key g: " << color.key_g << "\n";
        infostream << "Color key b: " << color.key_b << "\n";
    }
    infostream << "Texts: " << info.text_num << "\n";
    for (size_t i = 0; i < info.text_num; i++) {
        infostream << "Text: " << info.text_keys[i] << ": " << info.text_strings[i] << "\n";
    }
    infostream << "International texts: " << info.itext_num << "\n";
    for (size_t i = 0; i < info.itext_num; i++) {
        infostream << "Text: "
        << info.itext_keys[i] << ", "
        << info.itext_langtags[i] << ", "
        << info.itext_transkeys[i] << ": "
        << info.itext_strings[i];
    }
    infostream << "Time defined: " << info.time_defined << "\n";
    if (info.time_defined) {
        const LodePNGTime &time = info.time;
        infostream << "year: " << time.year << "\n";
        infostream << "month: " << time.month << "\n";
        infostream << "day: " << time.day << "\n";
        infostream << "hour: " << time.hour << "\n";
        infostream << "minute: " << time.minute << "\n";
        infostream << "second: " << time.second << "\n";
    }
    infostream << "Physics defined: " << info.phys_defined << "\n";
    if (info.phys_defined) {
        infostream << "physics X: " << info.phys_x << "\n";
        infostream << "physics Y: " << info.phys_y << "\n";
        infostream << "physics unit: " << info.phys_unit << "\n";
    }


    pngdetail += infostream.str();

    return strdup(pngdetail.c_str());
}
}
