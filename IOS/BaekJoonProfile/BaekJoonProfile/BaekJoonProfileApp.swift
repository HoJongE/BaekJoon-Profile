//
//  BaekJoonProfileApp.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/10.
//

import SwiftUI
import SDWebImage
import SDWebImageSVGCoder
import Firebase

@main
struct BaekJoonProfileApp: App {
  @StateObject var profileViewModel = ProfileViewModel(profileRepository: DefaultProfileRepository.shared)
  @StateObject var sheetManager = SheetManager()
  
  init(){
    FirebaseApp.configure()
    SDImageCodersManager.shared.addCoder(SDImageSVGCoder.shared)
  }
  var body: some Scene {
    
    WindowGroup {
      RootView()
        .environmentObject(profileViewModel)
        .environmentObject(sheetManager)
        .onOpenURL { url in
          parseURL(url: url)
        }
    }
  }
  
  private func parseURL(url : URL) {
    guard url.absoluteString.starts(with: Const.URL.WIDGET_ACTION) else {
      return
    }
    guard let urlComponents = URLComponents(string: url.absoluteString) else {return}
    guard let id = urlComponents.queryItems?.first(where: {$0.name == "id"})?.value else {return}
    profileViewModel.getProfile(id: id)
  }
}
