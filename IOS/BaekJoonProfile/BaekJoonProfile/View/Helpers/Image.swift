//
//  Image.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/15.
//

import SwiftUI
import SDWebImageSwiftUI

struct CircleImage: View {
  let url : String
  let width : CGFloat
  var body: some View {
    WebImage(url: URL(string: url))
      .resizable()
      .placeholder {
        Rectangle().foregroundColor(.white)
      }
      .indicator(.activity)
      .clipShape(Circle())
      .aspectRatio(1, contentMode: .fit)
      .frame(width:width)
      .overlay(Circle().stroke(Color.white,lineWidth: 2))
      .shadow(radius: 5)
    
  }
}

struct SquareImage : View {
  let url : String
  
  var body: some View {
    WebImage(url:URL(string: url))
      .resizable()
      .indicator(.activity)
      .aspectRatio(1, contentMode: .fit)
    
  }
}
struct Image_Previews: PreviewProvider {
  static var previews: some View {
    Group {
      CircleImage(url: "https://static.solved.ac/uploads/profile/360x360/2dbfa96246ee0c02cf13a756d5ddd0ffb0ef978e.png",
                  width: 50)
      CircleImage(url: "https://nokiatech.github.io/heif/content/images/ski_jump_1440x96",
                  width: 50)
    }
    .previewLayout(.fixed(width: 70, height: 70))
  }
}
