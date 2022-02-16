//
//  Text.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/12.
//

import SwiftUI

struct LargeTitle: ViewModifier {
    var textColor : Color = Color.firstTextColor
    func body(content: Content) -> some View {
        content
            .font(.custom("GMarketSansTTFBold", size: 40))
            .lineSpacing(8)
            .foregroundColor(textColor)
    }
}

struct BodyText : ViewModifier {
    var textColor : Color = Color.firstTextColor
    func body(content: Content) -> some View {
        content
            .font(.custom("GMarketSansTTFMedium", size: 18))
            .lineSpacing(8)
            .foregroundColor(textColor)
    }
}

struct CaptionText : ViewModifier {
    var color  = Color.black
    func body(content: Content) -> some View {
        content
            .font(.custom("GMarketSansTTFMedium", size: 14))
            .lineSpacing(4)
            .foregroundColor(color)
            .multilineTextAlignment(.leading)
    }
}
struct ButtonText : ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.custom("GMarketSansTTFMedium", size: 18))
            .foregroundColor(.blue)
    }
}
