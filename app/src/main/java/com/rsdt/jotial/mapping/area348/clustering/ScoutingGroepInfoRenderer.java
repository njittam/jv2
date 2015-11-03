package com.rsdt.jotial.mapping.area348.clustering;

import android.content.Context;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import com.rsdt.jotial.data.structures.area348.receivables.ScoutingGroepInfo;
import com.rsdt.jotial.mapping.area348.JotiInfoWindowAdapter;
import com.rsdt.jotial.mapping.area348.behaviour.InfoBehaviour;
import com.rsdt.jotial.mapping.area348.data.MapData;
import com.rsdt.jotial.mapping.area348.data.MapPartState;
import com.rsdt.jotiv2.R;

/**
 * @author Dingenis Sieger Sinke
 * @version 1.0
 * @since 26-10-2015
 * The (cluster)renderer for the ScoutingGroepInfo.
 */
public class ScoutingGroepInfoRenderer extends DefaultClusterRenderer<ScoutingGroepInfo> {

    public ScoutingGroepInfoRenderer(Context context, GoogleMap map, ClusterManager<ScoutingGroepInfo> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(ScoutingGroepInfo item, MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.scouting_groep_icon_30x22));
        markerOptions.anchor(0.5f, 0.5f);

        /**
         * Use the title to save data with the marker.
         * */
        markerOptions.title("sc;" + item.naam + ";" + item.adres + ";" + item.latitude + ";" + item.longitude + ";" + item.deelgebied);
    }

    @Override
    protected void onClusterItemRendered(ScoutingGroepInfo clusterItem, Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<ScoutingGroepInfo> cluster, MarkerOptions markerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions);

        /**
         * Define it is a cluster by format Scouting Groep Cluster(scc).
         * NOTE: You can make a behaviour for this cluster.
         * */
        markerOptions.title("scc;" + cluster.getSize() + ";" + cluster.getPosition().latitude + ";" + cluster.getPosition().longitude);
    }

    @Override
    protected void onClusterRendered(Cluster<ScoutingGroepInfo> cluster, Marker marker) {
        super.onClusterRendered(cluster, marker);
    }

    @Override
    protected String getClusterText(int bucket) {
        return super.getClusterText(bucket);
    }
}