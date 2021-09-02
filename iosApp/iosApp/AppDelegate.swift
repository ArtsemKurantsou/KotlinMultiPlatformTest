//
//  AppDelegate.swift
//  iosApp
//
//  Created by Artem on 30.08.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    // Lazy so it doesn't try to initialize before startKoin() is called
    lazy var log = koin.get(objCClass: Kermit.self, parameter: "AppDelegate") as! Kermit
    
    var viewController: UINavigationController?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions
        launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {

        startKoin()

        self.viewController = UINavigationController()

        self.window = UIWindow(frame: UIScreen.main.bounds)
        self.window?.rootViewController = viewController
        self.window?.makeKeyAndVisible()
        navigateTo(controller: UIHostingController(rootView: NewsListScreen()), animated: false)

        log.v(message: {"App Started"})
        return true
    }
    
    func navigateTo(controller: UIViewController, animated: Bool = true) {
        viewController?.pushViewController(controller, animated: animated)
    }
}
