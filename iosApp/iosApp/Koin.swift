//
//  Koin.swift
//  iosApp
//
//  Created by Artem on 31.08.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

func startKoin() {
    
    let koinApplication = KoinActualKt.doInitKoinIos()
    _koin = koinApplication.koin
}

private var _koin: Koin_coreKoin? = nil
var koin: Koin_coreKoin {
    return _koin!
}
