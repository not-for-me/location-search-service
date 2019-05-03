import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { LocationSearchService } from "../services/location-search.service";
import { ResultContainer } from "../shared/result-container.model";
import { DynamicScriptLoaderService } from "../services/dynamic-script-loader.service";

declare var daum;

@Component({
  selector: 'lss-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Location Search Service';
  searchPlaceResults = [];
  @ViewChild('map')
  mapRef: ElementRef;

  private curInfoWindow: any;

  constructor(private locationSearchService: LocationSearchService,
              private dynamicScriptLoaderService: DynamicScriptLoaderService) {
  }

  ngOnInit(): void {
    this.loadDaumMap();
  }

  public search($event) {
    const keyword = $event;
    console.debug(`keyword: ${keyword}`);
    this.locationSearchService.search(keyword)
      .subscribe((r: ResultContainer) => {
        this.searchPlaceResults = r.data;
      });
  }

  public showDetail(place) {
    if (this.curInfoWindow) {
      this.curInfoWindow.close();
    }

    const markerPosition = new daum.maps.LatLng(place.lat, place.lon);
    const mapOption = {center: markerPosition, level: 3};

    const map = new daum.maps.Map(this.mapRef.nativeElement, mapOption);
    const marker = new daum.maps.Marker({position: markerPosition});

    marker.setMap(map);

    const iwContent = this.getInfoWindowTpl(place);
    const infoWindow = new daum.maps.InfoWindow({position: markerPosition, content: iwContent});

    this.curInfoWindow = infoWindow.open(map, marker);
  }

  private loadDaumMap() {
    this.dynamicScriptLoaderService.loadScript("daumMap").then(data => {
      daum.maps.load(() => {
        const options = {
          center: new daum.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
          level: 3
        };

        new daum.maps.Map(this.mapRef.nativeElement, options);
      });
    });
  }

  private getInfoWindowTpl(place) {
    return `<div style="padding:5px;display: inline-block;white-space: nowrap;">상호: ${place.placeName}<br/>` +
      `주소: ${place.address}<br/>` +
      `도로명주소: ${place.roadAddress}<br/>` +
      `연락처: ${place.phone}<br/>` +
      `<a href="${place.mapUrl}" target="_blank">카카오맵 바로가기</a>` +
      `</div>`;
  }
}
