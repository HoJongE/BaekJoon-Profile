//
//  ProfileDescription.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import SwiftUI

extension ProfileHost {
  struct ProfileDescription : View {
    let text : String
    var body: some View {
      
      Text(text)
        .captionText(textColor: .black)
        .frame(maxWidth:.infinity,alignment: .leading)
        .padding()
        .background(Color.white)
        .cornerRadius(8)
        .padding(.horizontal,32)
        .padding(.vertical,8)
      
    }
  }
}
struct ProfileDescription_Previews: PreviewProvider {
  static var previews: some View {
    ProfileHost.ProfileDescription(text: "안녕하세요")
      .previewLayout(.sizeThatFits)
      .background(Color.backgroundColor)
  }
}
