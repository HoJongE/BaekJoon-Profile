//
//  VerticalInfoView.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import SwiftUI


extension ProfileHost {
    struct VerticalInfoView : View {
        let solved : Int
        let rating : Int
        let rank : Int
        let width : CGFloat
        let textColor : Color
        var body: some View {
            VStack(alignment:.leading,spacing: 12) {
                Text("Solved")
                    .bodyText(textColor: .white)
                Text(String(solved))
                    .bodyText(textColor: textColor)
                    .padding(.bottom)
                Text("AC Rating")
                    .bodyText(textColor: .white)
                
                Text(String(rating))
                    .bodyText(textColor: textColor)
                    .padding(.bottom)
                Text("Rank")
                    .bodyText(textColor: .white)
                Text(String(rank))
                    .bodyText(textColor: textColor)
            }
            
            .frame(width:width,alignment: .leading)
            .padding(.leading,32)
            
        }
    }
}

struct VerticalInfoView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileHost.VerticalInfoView(
            solved: 300, rating: 300, rank: 300, width: 300, textColor: .white)
            .previewLayout(.sizeThatFits)
            .background(Color.backgroundColor)
    }
}
