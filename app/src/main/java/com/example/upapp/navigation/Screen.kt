package com.example.upapp.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Home : Screen("home_screen")
    object Support : Screen("support_screen")
    object ProfileCredential : Screen("profile_credential_screen")
    object VehiclePass : Screen("vehicle_pass_screen")
    object CalendarEvents : Screen("calendar_events_screen")
    object AgroAlerts : Screen("agro_alerts_screen")
    object Map : Screen("map_screen")
    object Documents : Screen("documents_screen")
    object Radio : Screen("radio_screen")
    object Settings : Screen("settings_screen")
    object HelpComments : Screen("help_comments")
    object Notifications : Screen("notifications_screen")

}