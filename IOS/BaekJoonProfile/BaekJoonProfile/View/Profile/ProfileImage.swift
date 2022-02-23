//
//  ProfileImage.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import SwiftUI

extension ProfileHost {
    struct ProfileImage : View {
        let url : String
        let tier : Int
        let width : CGFloat
        var body: some View {
            ZStack(alignment:.bottom) {
                CircleImage(url: url, width: width)
                TierBadge(width: width/5, tier: tier)
                    .offset(y:5)
            }
        }
    }
}

struct ProfileImage_Previews: PreviewProvider {
    static var previews: some View {
        ProfileHost.ProfileImage(url: Const.URL.DEFAULT_PROFILE, tier: 24, width: 150)
            .previewLayout(.sizeThatFits)
            .padding()
            .background(Color.backgroundColor)
    }
}
