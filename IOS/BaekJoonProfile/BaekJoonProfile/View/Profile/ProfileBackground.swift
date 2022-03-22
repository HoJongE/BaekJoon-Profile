//
//  ProfileBackground.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import SwiftUI
import SDWebImageSwiftUI

extension ProfileHost {
    struct BackgroundView : View {
        let url : String
        let width: CGFloat
        var body: some View {
            
            WebImage(url: URL(string : url))
                .resizable()
                .indicator(.activity)
                .scaledToFit()
                .frame(width: width,height: width * 0.33)
            
        }
    }
}
#if DEBUG
struct ProfileBackground_Previews: PreviewProvider {
    static var previews: some View {
        ProfileHost.BackgroundView(url: Const.MockedURL.backgroundURL, width: 300)
            .previewLayout(.sizeThatFits)
    }
}
#endif
