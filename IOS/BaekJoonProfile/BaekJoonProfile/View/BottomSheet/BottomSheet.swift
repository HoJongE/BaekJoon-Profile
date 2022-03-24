//
//  BottomSheet.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import SwiftUI


struct BottomSheetContainer<Content:View>: View {
  
  let title : String
  let content : () -> Content
  @Binding var isPresent : Bool
  
  init(title:String,isPresent : Binding<Bool> , @ViewBuilder content:@escaping () -> Content) {
    self.title = title
    self._isPresent = isPresent
    self.content = content
  }
  
  var body: some View {
    VStack{
      RoundedRectangle(cornerRadius: 20)
        .frame(width: 50, height: 5, alignment: .center)
        .foregroundColor(.gray)
        .padding()
      
      HStack{
        Text(title)
          .bodyText(textColor: .white)
          .padding()
        Spacer()
        CloseButton {
          isPresent = false
        }
        .padding(.trailing)
      }
      content()
      Spacer()
    }
    .foregroundColor(.white)
    .frame(maxWidth:.infinity,maxHeight: .infinity)
    .background(Color.backgroundColor)
  }
}
struct BottomSheet_Previews: PreviewProvider {
  static var previews: some View {
    BottomSheetContainer(title: "안내", isPresent: .constant(true)){
      Text("하하")
    }
  }
}
