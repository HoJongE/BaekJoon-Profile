//
//  Text.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/12.
//

import SwiftUI

struct LargeTitleModifier: ViewModifier {
    var textColor : Color = Color.firstTextColor
    func body(content: Content) -> some View {
        
        content
            .font(.custom("GMarketSansTTFBold", size: 40))
            .lineSpacing(8)
            .foregroundColor(textColor)
    }
}

struct BodyTextModifier : ViewModifier {
    var textColor : Color = Color.firstTextColor
    func body(content: Content) -> some View {
        content
            .font(.custom("GMarketSansTTFMedium", size: 18))
            .lineSpacing(8)
            .foregroundColor(textColor)
    }
}

struct CaptionTextModifier : ViewModifier {
    var color  = Color.black
    func body(content: Content) -> some View {
        content
            .font(.custom("GMarketSansTTFMedium", size: 14))
            .lineSpacing(4)
            .foregroundColor(color)
            .multilineTextAlignment(.leading)
    }
}
struct ButtonTextModifier : ViewModifier {
    var color = Color.black
    func body(content: Content) -> some View {
        content
            .font(.custom("GMarketSansTTFMedium", size: 18))
            .foregroundColor(color)
    }
}

struct Text_Previews : PreviewProvider {
    static var previews: some View {
        VStack {
            Text("LargeTitle")
                .largeTitle(textColor: .black)
            Text("BodyText")
                .bodyText(textColor: .black)
            Text("CaptionText")
                .captionText(textColor: .black)
            Text("ButtonText")
                .buttonText(textColor: .black)
        }
    }
}

extension Text {
    
    func largeTitle(textColor:Color) -> some View {
        self
            .baselineOffset(8)
            .modifier(LargeTitleModifier(textColor: textColor))
    }
    
    func captionText(textColor:Color) -> some View {
        self.baselineOffset(2.5)
            .modifier(CaptionTextModifier(color: textColor))
    }
    func bodyText(textColor:Color) -> some View {
        self.baselineOffset(2.5)
            .modifier(BodyTextModifier(textColor: textColor))
    }
    
    func buttonText(textColor:Color) -> some View {
        self.baselineOffset(2.5)
            .modifier(ButtonTextModifier(color: textColor))
    }
}
