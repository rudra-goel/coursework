function nanometersToRGB(nm) {
  var w = parseInt(nm);
  var R, G, B;
  if (w >= 380 && w < 440) {
        R = -(w - 440) / (440 - 350);
        G = 0.0;
        B = 1.0;
    } else if (w >= 440 && w < 490) {
        R = 0.0
        G = (w - 440) / (490 - 440);
        B = 1.0;
    } else if (w >= 490 && w < 510) {
        R = 0.0;
        G = 1.0;
        B = -(w - 510.) / (510 - 490);
    } else if (w >= 510 && w < 580) {
        R = (w - 510) / (580 - 510);
        G = 1.0;
        B = 0.0;
    } else if (w >= 580 && w < 645) {
        R = 1.0;
        G = -(w - 645) / (645 - 580);
        B = 0.0;
    } else if (w >= 645 && w <= 780) {
        R = 1.0;
        G = 0.0;
        B = 0.0;
    } else {
        R = 0.0;
        G = 0.0;
        B = 0.0;
    }

    // Intensity correction
    var rgbIntensity;
    if (w >= 380 && w < 420) {
        rgbIntensity = 0.3 + 0.7 * (w - 350) / (420 - 350);
    } else if (w >= 420 && w <= 700) {
        rgbIntensity = 1.0;
    } else if (w > 700 && w <= 780) {
        rgbIntensity = 0.3 + 0.7 * (780 - w) / (780 - 700);
    } else {
        rgbIntensity = 0.0;
    }
    rgbIntensity *= 255;

    return [parseInt(R * rgbIntensity), parseInt(G * rgbIntensity), parseInt(B * rgbIntensity)];
}

function nanometersToRGBString(nm) {
    var rgbArray = nanometersToRGB(nm);
    return 'rgb(' + rgbArray[0] + ',' + rgbArray[1] + ',' + rgbArray[2] + ')';
}

function displaySpectrumInElement(linesArray, el) {
    $(el).empty();
    for (var nm = 380; nm <= 780; nm++) {
       if ($.inArray(nm, linesArray) != -1) {
           op = '1.0';
           wavelengthDisplay = '<span style="display: inline; width: 0; color: ' + nanometersToRGBString(nm) + ';">' + nm + ' nm</span>';
       } else {
           op = '0.1';
           wavelengthDisplay = '';
       }
       $(el).append('<div style="display: inline-block; width: 1px; height: 100%; background-color: ' + nanometersToRGBString(nm) + '; opacity: ' + op + ';"></div>');
   }
}