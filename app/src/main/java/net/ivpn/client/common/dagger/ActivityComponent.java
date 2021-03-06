package net.ivpn.client.common.dagger;

import com.wireguard.android.backend.WireGuardUiService;

import net.ivpn.client.common.shortcuts.ConnectionShortcutsActivity;
import net.ivpn.client.ui.alwaysonvpn.AlwaysOnVpnActivity;
import net.ivpn.client.ui.billing.BillingActivity;
import net.ivpn.client.ui.connect.ConnectActivity;
import net.ivpn.client.ui.customdns.CustomDNSActivity;
import net.ivpn.client.ui.customdns.DialogueCustomDNSViewModel;
import net.ivpn.client.ui.dialog.LocationDialogViewModel;
import net.ivpn.client.ui.login.LoginActivity;
import net.ivpn.client.ui.network.NetworkActivity;
import net.ivpn.client.ui.network.NetworkRecyclerViewAdapter;
import net.ivpn.client.ui.network.rules.NetworkRuleActivity;
import net.ivpn.client.ui.privateemails.PrivateEmailsActivity;
import net.ivpn.client.ui.privateemails.edit.EditPrivateEmailActivity;
import net.ivpn.client.ui.protocol.ProtocolActivity;
import net.ivpn.client.ui.serverlist.ServersListActivity;
import net.ivpn.client.ui.serverlist.ServersRecyclerViewAdapter;
import net.ivpn.client.ui.serverlist.all.ServersListFragment;
import net.ivpn.client.ui.serverlist.fastest.FastestSettingActivity;
import net.ivpn.client.ui.serverlist.favourites.FavouriteServersListFragment;
import net.ivpn.client.ui.settings.SettingsActivity;
import net.ivpn.client.ui.signup.SignUpActivity;
import net.ivpn.client.ui.split.SplitTunnelingActivity;
import net.ivpn.client.ui.startonboot.StartOnBootActivity;
import net.ivpn.client.ui.subscription.SubscriptionActivity;
import net.ivpn.client.ui.subscription.monthly.MonthlySubscriptionFragment;
import net.ivpn.client.ui.subscription.yearly.YearlySubscriptionFragment;
import net.ivpn.client.ui.surveillance.AntiSurveillanceActivity;
import net.ivpn.client.ui.syncservers.SyncServersActivity;
import net.ivpn.client.ui.timepicker.TimePickerActivity;
import net.ivpn.client.ui.updates.UpdatesActivity;
import net.ivpn.client.vpn.AlwaysOnVpnService;
import net.ivpn.client.vpn.OnBootBroadcastReceiver;
import net.ivpn.client.vpn.controller.WireGuardKeyBroadcastReceiver;
import net.ivpn.client.vpn.local.PermissionActivity;
import net.ivpn.client.vpn.openvpn.IVPNService;

import dagger.Subcomponent;
import de.blinkt.openvpn.core.Connection;

@ActivityScope
@Subcomponent
public interface ActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        ActivityComponent create();
    }

    void inject(SignUpActivity activity);

    void inject(SettingsActivity activity);

    void inject(SplitTunnelingActivity activity);

    void inject(AntiSurveillanceActivity activity);

    void inject(LoginActivity activity);

    void inject(ProtocolActivity activity);

    void inject(ConnectActivity activity);

    void inject(NetworkActivity activity);

    void inject(NetworkRuleActivity activity);

    void inject(UpdatesActivity activity);

    void inject(AlwaysOnVpnActivity activity);

    void inject(CustomDNSActivity activity);

    void inject(PrivateEmailsActivity activity);

    void inject(EditPrivateEmailActivity activity);

    void inject(ServersListActivity activity);

    void inject(SyncServersActivity activity);

    void inject(FastestSettingActivity activity);

    void inject(StartOnBootActivity activity);

    void inject(TimePickerActivity activity);

    void inject(PermissionActivity activity);

    void inject(SubscriptionActivity activity);

    void inject(BillingActivity activity);

    void inject(ConnectionShortcutsActivity activity);

    void inject(ServersListFragment fragment);

    void inject(FavouriteServersListFragment fragment);

    void inject(YearlySubscriptionFragment fragment);

    void inject(MonthlySubscriptionFragment fragment);

    void inject(WireGuardKeyBroadcastReceiver receiver);

    void inject(OnBootBroadcastReceiver receiver);

    void inject(IVPNService service);

    void inject(WireGuardUiService service);

    void inject(AlwaysOnVpnService service);

    void inject(NetworkRecyclerViewAdapter.CommonNetworkViewHolder viewHolder);

    void inject(NetworkRecyclerViewAdapter.WifiItemViewHolder viewHolder);

    void inject(ServersRecyclerViewAdapter.ServerViewHolder viewHolder);

    void inject(Connection connection);

    DialogueCustomDNSViewModel getDialogueViewModel();

    LocationDialogViewModel getLocationDialogueViewModel();
}
