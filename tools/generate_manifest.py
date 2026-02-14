#!/usr/bin/env python3
"""
Yoshinkan Video Manifest Generator

Scans a directory for video files matching the naming convention
and generates a video_manifest.json file.

Usage:
    python generate_manifest.py "C:\path\to\video\directory"
"""

import json
import os
import sys
from pathlib import Path

# Attack ID mapping
ATTACKS = {
    "KATA_MOCHI": 1,
    "KATATE_AYAGYAKU_MOCHI": 2,
    "KATATE_AYAJUN_MOCHI": 3,
    "KATATE_MOCHI": 4,
    "MUNE_MOCHI": 5,
    "RYOKATA_MOCHI": 6,
    "RYOSODE_MOCHI": 7,
    "RYOTE_MOCHI": 8,
    "SHOMEN_UCHI": 9,
    "SODE_MOCHI": 10,
    "SUIGETSU_ZUKI": 11,
    "USHIRO_WAZA_ERI_MOCHI": 12,
    "USHIRO_WAZA_KATATE_ERI_MOCHI": 13,
    "USHIRO_WAZA_RYOHIJI_MOCHI": 14,
    "USHIRO_WAZA_RYOTE_MOCHI": 15,
    "YOKOMEN_UCHI": 16,
}

# Technique ID mapping
TECHNIQUES = {
    "HIJI_SHIME": 1,
    "IKKAJO_OSAE": 2,
    "IRIMI_TSUKI": 3,
    "NIKAJO_OSAE": 4,
    "SANKAJO_OSAE": 5,
    "YONKAJO_OSAE": 6,
    "IRIMI_NAGE": 7,
    "KOTEGAESHI_OSAE": 8,
    "HIJI_ATE": 9,
    "SHIHO_NAGE": 10,
    "SOKUMEN_IRIMI_NAGE": 11,
    "TENCHI_NAGE": 12,
}

# Energy ID mapping
ENERGIES = {
    "ICHI": 1,
    "NI": 2,
}


def get_technique_id(technique_name):
    """Get technique ID from technique name"""
    return TECHNIQUES.get(technique_name)


def get_attack_id(attack_name):
    """Get attack ID from attack name"""
    return ATTACKS.get(attack_name)


def get_energy_id(energy_name):
    """Get energy ID from energy name"""
    return ENERGIES.get(energy_name.upper())


def identify_technique_part(parts):
    """Identify the technique portion of the filename"""
    # Work backwards from the energy part
    parts_without_energy = parts[:-1]  # Remove energy

    # Try matching techniques from longest to shortest
    sorted_techniques = sorted(TECHNIQUES.keys(), key=len, reverse=True)

    for technique in sorted_techniques:
        technique_parts = technique.split("_")
        if len(parts_without_energy) >= len(technique_parts):
            potential_technique = "_".join(parts_without_energy[-len(technique_parts):])
            if potential_technique == technique:
                return technique

    return None


def parse_video_filename(filename):
    """
    Parse a video filename and extract attack, technique, and energy IDs

    Filename format: {ATTACK}_{TECHNIQUE}_{ENERGY}.mp4
    Example: SUIGETSU_ZUKI_IKKAJO_OSAE_ICHI.mp4
    """
    try:
        # Remove .mp4 extension
        name_without_ext = filename[:-4] if filename.endswith('.mp4') else filename
        parts = name_without_ext.split("_")

        if len(parts) < 3:
            return None

        # Energy is always the last part
        energy_part = parts[-1]
        energy_id = get_energy_id(energy_part)
        if energy_id is None:
            return None

        # Find the technique part
        technique_name = identify_technique_part(parts)
        if technique_name is None:
            return None

        technique_id = get_technique_id(technique_name)
        if technique_id is None:
            return None

        # Get attack part (everything before technique)
        technique_parts = technique_name.split("_")
        parts_to_remove = len(technique_parts) + 1  # +1 for energy

        if len(parts) <= parts_to_remove:
            return None

        attack_parts = parts[:-parts_to_remove]
        attack_name = "_".join(attack_parts)
        attack_id = get_attack_id(attack_name)
        if attack_id is None:
            return None

        display_name = f"{attack_name} - {technique_name} - {energy_part}"

        return {
            "fileName": filename,
            "attackId": attack_id,
            "techniqueId": technique_id,
            "energyId": energy_id,
            "displayName": display_name
        }
    except Exception as e:
        return None


def scan_video_directory(directory_path):
    """Scan a directory for video files and generate manifest"""
    video_dir = Path(directory_path)

    if not video_dir.exists() or not video_dir.is_dir():
        print(f"Error: Directory does not exist or is not a directory: {directory_path}")
        return None

    video_entries = []

    # Find all MP4 files
    for file in sorted(video_dir.glob("*.mp4")):
        if file.is_file():
            video_entry = parse_video_filename(file.name)
            if video_entry is not None:
                video_entries.append(video_entry)
            else:
                print(f"Warning: Could not parse filename: {file.name}")

    return {
        "videos": sorted(video_entries, key=lambda x: x["fileName"])
    }


def main():
    if len(sys.argv) < 2:
        print("Usage: python generate_manifest.py <path_to_video_directory>")
        print()
        print("Example:")
        print('  python generate_manifest.py "C:\\Users\\Jim\\claude-skills\\video-decomposer\\data\\Tachi Waza - Clean"')
        return

    video_directory = sys.argv[1]

    print("=" * 70)
    print("Yoshinkan Video Manifest Generator")
    print("=" * 70)
    print(f"Scanning directory: {video_directory}")
    print()

    manifest = scan_video_directory(video_directory)

    if manifest is None:
        print("Error: Failed to scan directory")
        return

    if not manifest["videos"]:
        print("No valid video files found!")
        return

    print(f"Found {len(manifest['videos'])} video files")
    print()

    # Save manifest to file
    output_file = Path(video_directory) / "video_manifest.json"

    try:
        with open(output_file, 'w') as f:
            json.dump(manifest, f, indent=2)

        print(f"✓ Manifest saved to: {output_file}")
        print()
        print("Video Summary:")
        for video in manifest["videos"]:
            print(f"  ✓ {video['fileName']}")

        print()
        print("=" * 70)
        print("SUCCESS! Manifest generated successfully.")
        print("=" * 70)

    except Exception as e:
        print(f"Error: Failed to save manifest file: {e}")


if __name__ == "__main__":
    main()
