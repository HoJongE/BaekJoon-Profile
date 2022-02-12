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
