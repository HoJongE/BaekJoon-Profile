//
//  IdListView.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/21.
//

import SwiftUI

struct IdListView: View {
    let idList : [String]
    let onDelete : (Optional<IndexSet>) -> Void
    let onMove : Optional<(IndexSet,Int) -> Void>
    let onIdClick : (String) -> Void
    var body: some View {
        VStack{
            EditButton()
                .font(.custom("GmarketSansTTFMedium", size: 14))
                .foregroundColor(.white)

            List {
                ForEach(idList,id: \.self){ id in
                    Button(action:{onIdClick(id)}){
                        Text(id)
                            .bodyText(textColor: .white)
                    }
                }
                .onDelete(perform: onDelete)
                .onMove(perform: onMove)
            }
        }
        .background(Color.backgroundColor)
    }
    
}
#if DEBUG
struct IdListView_Previews: PreviewProvider {
    static var previews: some View {
        BottomSheetContainer(title: "최근 조회목록", isPresent: .constant(true)){
            IdListView(idList: mockedIDList,onDelete: {indexSet in },onMove: nil, onIdClick: {_ in })
        }
    }
}
#endif
