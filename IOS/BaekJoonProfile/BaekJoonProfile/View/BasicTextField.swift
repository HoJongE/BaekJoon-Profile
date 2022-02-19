//
//  TextField.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/11.
//

import SwiftUI

struct BasicTextField: View {
    let error : Bool
    let placeHolderText : String
    @Binding var value : String
    var body: some View {
        if #available(iOS 15.0, *) {
            ZStack{
                HStack{
                    Text(placeHolderText)
                        .modifier(BodyText())
                        .padding(.leading,16)
                    TextField("",text: $value)
                        .modifier(BodyText(textColor: Color.white))
                        .padding(.leading,8)
                }
                VStack{
                    Spacer()
                    Divider().frame(height:2).background(error ? Color.errorColor : Color.white)
                }
            }
            .frame(height:60)
            .background(Color.editTextColor)
            .cornerRadius(15, corners: .topLeft)
            .cornerRadius(15, corners: .topRight)
            .shadow(radius: 4)
            .padding(.horizontal,24)
            .textInputAutocapitalization(.never)
        } else {
            ZStack{
                HStack{
                    Text(placeHolderText)
                        .modifier(BodyText())
                        .padding(.leading,16)
                    TextField("",text: $value)
                        .modifier(BodyText(textColor: Color.white))
                        .padding(.leading,8)
                }
                VStack{
                    Spacer()
                    Divider().frame(height:2).background(error ? Color.errorColor : Color.white)
                }
            }
            .frame(height:60)
            .background(Color.editTextColor)
            .cornerRadius(15, corners: .topLeft)
            .cornerRadius(15, corners: .topRight)
            .shadow(radius: 4)
            .padding(.horizontal,24)
            .autocapitalization(.none)
        }
        
    }
}
extension View {
    func cornerRadius(_ radius: CGFloat, corners: UIRectCorner) -> some View {
        clipShape( RoundedCorner(radius: radius, corners: corners) )
    }
}
struct RoundedCorner: Shape {
    
    var radius: CGFloat = .infinity
    var corners: UIRectCorner = .allCorners
    
    func path(in rect: CGRect) -> Path {
        let path = UIBezierPath(roundedRect: rect, byRoundingCorners: corners, cornerRadii: CGSize(width: radius, height: radius))
        return Path(path.cgPath)
    }
}
struct TextField_Previews: PreviewProvider {
    static var previews: some View {
        VStack{
            BasicTextField(error: false, placeHolderText: "아이디",value: .constant("as00098"))
                .previewDevice(PreviewDevice(rawValue:"iPhone 8"))
            BasicTextField(error: true, placeHolderText: "아이디", value: .constant(""))
        }.background(Color.backgroundColor)
        
    }
}
