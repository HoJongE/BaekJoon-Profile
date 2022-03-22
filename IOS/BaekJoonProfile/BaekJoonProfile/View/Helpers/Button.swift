//
//  Button.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/14.
//

import SwiftUI

struct TextButton: View {
    let text : String
    let loading : Bool
    let onClick:()->Void
    
    var body: some View {
        Button(action: onClick){
            if loading {
                ProgressView()
                    .foregroundColor(.blue)
            } else {
                Text(text)
                    .buttonText(textColor: .blue)
            }
        }
    }
}

struct CloseButton : View {
    let onClick : () -> Void
    var body: some View {
        Button(action:onClick ) {
            Image(systemName: "xmark.circle.fill")
                .foregroundColor(.gray)
                .imageScale(.large)
                .padding(8)
        }
        
        
    }
}

struct BottomButton : View {
    let label : String
    let loading : Bool
    let onClick: () -> ()
    
    var body: some View {
        Button(action: onClick){
            ZStack {
                RoundedRectangle(cornerRadius: 8)
                    .foregroundColor(loading ? .gray : .white)
                    .frame(maxWidth:.infinity, maxHeight: 50, alignment: .center)
                if !loading {
                    Text(label)
                        .buttonText(textColor: .black)
                } else {
                    ProgressView()
                }
            }
        }
        .padding(.init(top: 8, leading: 24, bottom: 8, trailing: 24))
    }
}

struct BottomSheetActionButton : View {
    
    @EnvironmentObject var sheetManager : SheetManager
    
    var body: some View {

        HStack(spacing:0) {
           
            ForEach(SheetManager.SheetAction.allCases,id: \.self) { action in
                Button(action:{
                    sheetManager.sheetAction = action
                    sheetManager.isPresent = true
                }){
                    Label(action.rawValue, systemImage: action.imageName())
                        .labelStyle(VerticalLabelStyle())
                }
                .frame(maxWidth:.infinity)

                if action != .ProblemRecommend {
                    Divider()
                        .background(Color.white)
                        .frame(height:24)
                }
            }
        }
        .imageScale(.large)
        .foregroundColor(.firstTextColor)
        .padding(12)
       
    }
}

struct VerticalLabelStyle : LabelStyle {
    func makeBody(configuration: Configuration) -> some View {
        VStack {
            configuration.icon
                .imageScale(.large)
                .padding(4)
            configuration.title
                .font(.custom("GMarketSansTTFMedium", size: 14))
        }
    }
}
#if DEBUG
struct ButtonPreview : PreviewProvider {
    static var previews: some View {
        Group {
            TextButton(text:"프로필 조회", loading: true){
            }
            TextButton(text: "프로필 조회", loading: false, onClick: {})
            CloseButton{}
            BottomButton(label: "닫기",loading: true) {
                
            }
            BottomSheetActionButton().environmentObject(SheetManager())
        }
        .previewLayout(.sizeThatFits)
        .background(Color.backgroundColor)
        
    }
}
#endif
