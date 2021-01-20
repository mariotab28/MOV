using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore {
    public class Ad : MonoBehaviour
    {
        public void RewardedAdHints()
        {
            AdManager.ShowRewardedAd(SuccessHint, Skip, Failed);
        }

        public void StandardAd()
        {
            AdManager.ShowStandardAd();
        }

        public void ShowBanner()
        {
            AdManager.ShowBanner();
        }

        public void HideBanner()
        {
            AdManager.HideBanner();

        }

        public void NoMoreAds()
        {
            AdManager.DeactivateAds();
            GameManager.instance.NoMoreAds();

        }

        // Incrementa el nº de pistas del jugador y guarda la partida para recordar el nuevo número
        void SuccessHint()
        {
            GameManager.instance.AddHints(1);
        }

        void Skip()
        {
            Debug.Log("Ad skipped");
        }

        void Failed()
        {
            Debug.Log("Ad failed to load");
        }
    }
}
