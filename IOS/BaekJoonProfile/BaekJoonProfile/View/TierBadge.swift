//
//  TierBadge.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/17.
//

import SwiftUI

struct TierBadge: View {
    let width : CGFloat
    let tier: Int
    var body: some View {
        ZStack(alignment:.center) {
            TierBadgeShape()
                .frame(width:width,height: width)
                .foregroundColor(Profile.getTierColor(tier: tier))
                .shadow(radius:3)
           TierBadgeMask()
                .frame(width: width, height: width)
                .foregroundColor(.white)

            Text(String(Profile.getTierNumber(tier:tier)))
                .fontWeight(.bold)
                .font(.system(size: width * 0.8))
                .foregroundColor(.white)
                .frame(width: width, height: width, alignment: .center)
        }
    }
}
struct TierBadgeShape : Shape {
    
    func path(in rect: CGRect) -> Path {
        let path = Path { path in
            let bottomOfRect : CGFloat = rect.width * 1.09 //직사각형의 바닥 좌표
            let heightOfMask = rect.midX / 1.8 //직사각형 밑 삼각형의 높이
            
            path.move(to: CGPoint(x: rect.minX, y: rect.minY))
            path.addLine(to: CGPoint(x: rect.minX, y: bottomOfRect))
            path.addLine(to: CGPoint(x: rect.midX, y: bottomOfRect + heightOfMask))
            path.addLine(to: CGPoint(x: rect.maxX, y: bottomOfRect))
            path.addLine(to: CGPoint(x: rect.maxX, y: rect.minY))
            path.closeSubpath()
        }
        
        return path
        
    }
}

struct TierBadgeMask : Shape {
    func path(in rect: CGRect) -> Path {
        let bottomOfRect : CGFloat = rect.width * 1.09 //직사각형의 바닥 좌표
        let height = bottomOfRect * 0.1 //V 자 모양의 높이
        let heightOfMask = rect.midX / 1.8 //직사각형 밑 삼각형의 높이
        let startOfMaskY = bottomOfRect - (height * 2) //V자 모양 시작 Y좌표
        var path = Path()
        path.move(to: CGPoint(x: rect.minX, y:startOfMaskY ))
        path.addLine(to: CGPoint(x: rect.minX, y: startOfMaskY + height))
        path.addLine(to: CGPoint(x: rect.midX, y: startOfMaskY + heightOfMask + height))
        path.addLine(to: CGPoint(x: rect.maxX, y: startOfMaskY+height))
        path.addLine(to: CGPoint(x: rect.maxX, y: startOfMaskY))
        path.addLine(to: CGPoint(x: rect.midX, y: startOfMaskY+heightOfMask))
        path.closeSubpath()
        
        return path
    }
}
struct TierBadge_Previews: PreviewProvider {
    static var previews: some View {
        TierBadge(width: 50,tier : 24)
    }
}
